<script setup>
import { ref, computed, onMounted, onBeforeUnmount, watch } from "vue";
import * as echarts from "echarts";
import { advicePhysiologicalChartService } from "@/api/advice.js";

// 数据部分
const physiological = ref([]);
const chartRef = ref(null);
const villageChartRef = ref(null);
let chartInstance = null;
let villageChartInstance = null;

// 获取健康数据
const fetchPhysiologicalData = async () => {
  let result = await advicePhysiologicalChartService();
  physiological.value = result.data;
};

// 健康率计算
const healthRate = computed(() => {
  if (!physiological.value.length) return 0;
  const healthyCount = physiological.value.filter((item) => item.state === "健康").length;
  return Math.round((healthyCount / physiological.value.length) * 100);
});

// 虚拟村庄数据
const villages = ref([
  { name: "本村", rate: computed(() => healthRate.value), color: "#67C23A" },
  { name: "张家村", rate: 82, color: "#409EFF" },
  { name: "李家寨", rate: 75, color: "#E6A23C" },
  { name: "王家屯", rate: 88, color: "#F56C6C" },
]);

// 统一图表配置生成器
const getChartOption = (isMainChart = true) => {
  if (isMainChart) {
    return {
      title: {
        text: "居民健康分析",
        left: "center",
        textStyle: {
          color: "#606266",
          fontSize: 16,
          fontWeight: "normal",
        },
      },
      tooltip: {
        trigger: "item",
        formatter: ({ seriesName, name, value }) =>
          `${seriesName}<br/>${name}:${value}% (${(
            (value / 100) *
            physiological.value.length
          ).toFixed(0)}人)`,
      },
      legend: {
        orient: "vertical",
        left: "left",
        top: "15%",
        itemGap: 12,
        textStyle: {
          color: "#909399",
        },
      },
      series: [
        {
          name: "健康状态分布",
          type: "pie",
          radius: ["55%", "75%"],
          center: ["50%", "55%"],
          avoidLabelOverlap: true,
          itemStyle: {
            borderWidth: 2,
            borderColor: "#fff",
          },
          label: {
            show: false,
            position: "center",
            fontSize: 18,
            fontWeight: "bold",
          },
          emphasis: {
            label: {
              show: true,
              formatter: ({ percent }) => `${(percent * 100).toFixed(1)}%`,
              fontSize: 24,
              color: "#303133",
            },
          },
          data: [
            {
              value: healthRate.value,
              name: "健康",
              itemStyle: {
                color: "#67C23A",
                shadowBlur: 10,
                shadowColor: "rgba(103,194,58,0.5)",
              },
            },
            {
              value: 100 - healthRate.value,
              name: "需关注",
              itemStyle: {
                color: "#E6A23C",
                shadowBlur: 10,
                shadowColor: "rgba(230,162,60,0.5)",
              },
            },
            {
              value: 0,
              name: "异常",
              itemStyle: {
                color: "#F56C6C",
                shadowBlur: 10,
                shadowColor: "rgba(245,108,108,0.5)",
              },
              tooltip: { show: false },
            },
          ],
        },
      ],
      graphic: [
        {
          type: "text",
          left: "center",
          top: "45%",
          style: {
            text: "健康率",
            fill: "#909399",
            fontSize: 14,
          },
        },
        {
          type: "text",
          left: "center",
          top: "52%",
          style: {
            text: `${healthRate.value}%`,
            fill: "#303133",
            fontSize: 28,
            fontWeight: "bold",
          },
        },
      ],
    };
  }

  return {
    title: {
      text: "各村健康对比",
      left: "center",
      textStyle: {
        color: "#606266",
        fontSize: 16,
        fontWeight: "normal",
      },
    },
    tooltip: {
      trigger: "item",
      formatter: ({ name, value }) => `${name}<br/>健康率: ${value}%`,
    },
    series: [
      {
        type: "pie",
        radius: ["40%", "70%"],
        roseType: "radius",
        itemStyle: {
          borderRadius: 8,
          borderWidth: 2,
          borderColor: "#fff",
        },
        data: villages.value.map((village) => ({
          name: village.name,
          value: typeof village.rate === "number" ? village.rate : village.rate.value,
          itemStyle: {
            color: village.color,
            shadowBlur: 8,
            shadowColor: `${village.color}33`,
          },
        })),
      },
    ],
  };
};

// 图表操作
const initCharts = () => {
  // 主图表
  chartInstance = echarts.init(chartRef.value);
  chartInstance.setOption(getChartOption(true));

  // 村庄对比图表
  villageChartInstance = echarts.init(villageChartRef.value);
  villageChartInstance.setOption(getChartOption(false));
};

const updateCharts = () => {
  chartInstance?.setOption(getChartOption(true));
  villageChartInstance?.setOption(getChartOption(false));
};

// 窗口调整处理
const handleResize = () => {
  chartInstance?.resize();
  villageChartInstance?.resize();
};

// 生命周期
onMounted(async () => {
  await fetchPhysiologicalData();
  initCharts();
  window.addEventListener("resize", handleResize);
});

onBeforeUnmount(() => {
  window.removeEventListener("resize", handleResize);
  chartInstance?.dispose();
  villageChartInstance?.dispose();
});

watch([physiological, healthRate], updateCharts);
</script>

<template>
  <el-row :gutter="24" class="chart-container">
    <el-col :span="12">
      <el-card class="chart-card">
        <div ref="chartRef" class="chart"></div>
        <div class="chart-footer">
          <el-statistic
            title="当前统计人数"
            :value="physiological.length"
            value-style="color: #409EFF; font-size: 1.2em"
          />
          <el-tag type="info" effect="plain" class="update-time">
            最后更新: {{ new Date().toLocaleTimeString() }}
          </el-tag>
        </div>
      </el-card>
    </el-col>

    <el-col :span="12">
      <el-card class="chart-card">
        <div ref="villageChartRef" class="chart"></div>
        <div class="legend-container">
          <div v-for="village in villages" :key="village.name" class="legend-item">
            <span class="color-marker" :style="{ backgroundColor: village.color }"></span>
            <span class="village-name">{{ village.name }}</span>
            <el-tag effect="dark" :color="village.color" class="rate-tag">
              {{ typeof village.rate === "number" ? village.rate : village.rate.value }}%
            </el-tag>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped>
.chart-container {
  margin: 1rem 0;
}

.chart-card {
  height: 480px;
  position: relative;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.chart {
  height: 360px;
  margin: -1rem;
}

.chart-footer {
  position: absolute;
  bottom: 1.5rem;
  width: calc(100% - 3rem);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.update-time {
  font-size: 0.8em;
  padding: 4px 8px;
}

.legend-container {
  position: absolute;
  bottom: 1.5rem;
  left: 50%;
  transform: translateX(-50%);
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1rem;
  width: 80%;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.color-marker {
  display: inline-block;
  width: 16px;
  height: 16px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.village-name {
  font-size: 0.9em;
  color: #606266;
  min-width: 60px;
}

.rate-tag {
  font-weight: 500;
  border: none;
  padding: 4px 8px;
}

@media (max-width: 768px) {
  .chart-container {
    flex-direction: column;
  }

  .el-col {
    width: 100%;
    margin-bottom: 1rem;
  }

  .chart-card {
    height: 360px;
  }

  .chart {
    height: 260px;
  }
}
</style>
