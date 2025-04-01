<template>
  <div class="health-chart-container">
    <div ref="chartContainer" class="chart"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from "vue";
import * as echarts from "echarts";
import { physiologicalListService } from "@/api/physiological.js";

// 响应式数据
const physiological = ref([]);

// 计算BMI函数
const calculateBMI = (height, weight) => {
  if (!height || !weight || height <= 0) return 0;
  return (weight / (height / 100) ** 2).toFixed(1);
};

// 获取数据
const fetchPhysiologicalData = async () => {
  try {
    const result = await physiologicalListService();
    // 添加BMI计算
    physiological.value = result.data.map((item) => ({
      ...item,
      bmi: calculateBMI(item.height, item.weight),
    }));
  } catch (error) {
    console.error("获取数据失败:", error);
  }
};

// 组件挂载时获取数据
onMounted(() => {
  fetchPhysiologicalData();
});

// 健康指标参考范围 (添加BMI范围)
const healthRanges = {
  temperature: { min: 36.0, max: 37.5 },
  bloodGlucose: { min: 3.9, max: 6.1 },
  bloodPressure: { min: 90, max: 140 },
  heart: { min: 60, max: 100 },
  bmi: { min: 18.5, max: 24 },
  step: { min: 5000, max: 10000 },
  sleep: { min: 6, max: 9 },
};

// 只显示关键指标
const displayKeys = [
  "temperature",
  "bloodGlucose",
  "bloodPressure",
  "heart",
  "bmi",
  "state",
];

const chartContainer = ref(null);
let chartInstance = null;

// 处理数据，只取最近5次
const processData = () => {
  const recentData = physiological.value.slice(-5).reverse();

  return {
    categories: recentData.map((_, index) => `记录 ${index + 1}`),
    series: displayKeys.map((key) => ({
      name: getDisplayName(key),
      data: recentData.map((item) => item[key] || 0),
      itemStyle: {
        color: (params) => {
          const value = recentData[params.dataIndex][key];
          if (key === "state") {
            return value === "健康" ? "#52c41a" : "#f5222d";
          }
          const range = healthRanges[key];
          if (!range) return "#1890ff";

          // 调整临界值判断逻辑
          const isCritical = value < range.min * 1.05 || value > range.max * 0.95;
          const isUnhealthy = value < range.min || value > range.max;

          if (isUnhealthy) return "#f5222d"; // 不健康 - 红色
          if (isCritical) return "#faad14"; // 临界值 - 黄色
          return "#52c41a"; // 健康 - 绿色
        },
      },
    })),
  };
};

// 获取显示名称
const getDisplayName = (key) => {
  const names = {
    temperature: "体温(℃)",
    bloodGlucose: "血糖(mmol/L)",
    bloodPressure: "血压(mmHg)",
    heart: "心率(bpm)",
    bmi: "BMI",
    step: "步数",
    sleep: "睡眠(小时)",
    state: "状态",
  };
  return names[key] || key;
};

// 初始化图表
const initChart = () => {
  if (!chartContainer.value) return;
  chartInstance = echarts.init(chartContainer.value);
  updateChart();
};

// 更新图表数据
const updateChart = () => {
  if (!chartInstance || physiological.value.length === 0) return;

  const { categories, series } = processData();

  const option = {
    title: {
      text: "最近五次健康数据概览",
      left: "center",
      textStyle: {
        fontSize: 16,
        fontWeight: "bold",
      },
    },
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow",
      },
      formatter: function (params) {
        let result = params[0].name + "<br/>";
        params.forEach((param) => {
          const value = param.value;
          const seriesName = param.seriesName;
          if (seriesName === "状态") {
            result += `${seriesName}: <span style="color:${
              value === "健康" ? "#52c41a" : "#f5222d"
            };font-weight:bold">${value}</span><br/>`;
          } else {
            const key =
              param.seriesName === "BMI" ? "bmi" : param.seriesName.split("(")[0];
            const range = healthRanges[key] || {};
            result += `${seriesName}: <span style="color:${param.color};font-weight:bold">${value}</span>`;
            if (range.min !== undefined) {
              result += ` (参考范围: ${range.min}-${range.max})`;
            }
            result += "<br/>";
          }
        });
        return result;
      },
    },
    legend: {
      data: series.map((s) => s.name),
      top: 30,
      itemWidth: 12,
      itemHeight: 12,
    },
    grid: {
      left: "5%",
      right: "5%",
      top: "5%",
      bottom: "5%",
      containLabel: true,
    },
    xAxis: {
      type: "value",
      boundaryGap: [0, 0.1],
      axisLabel: {
        fontSize: 12,
      },
    },
    yAxis: {
      type: "category",
      data: categories,
      axisLabel: {
        fontSize: 12,
        margin: 10,
      },
    },
    series: series.map((s) => ({
      ...s,
      type: "bar",
      barWidth: "15%", // 柱宽
      barGap: "30%", // 柱间间距
      label: {
        show: true,
        position: "right",
        fontSize: 11,
        formatter: function (params) {
          if (params.seriesName === "状态") {
            return params.value;
          }
          // 对BMI显示更友好
          if (params.seriesName === "BMI") {
            const bmi = parseFloat(params.value);
            let status = "";
            if (bmi < 18.5) status = "偏瘦";
            else if (bmi >= 18.5 && bmi <= 24) status = "正常";
            else if (bmi > 24 && bmi <= 28) status = "超重";
            else status = "肥胖";
            return `${params.value} (${status})`;
          }
          return params.value;
        },
      },
    })),
  };

  chartInstance.setOption(option);
};

// 监听数据变化
watch(
  physiological,
  () => {
    updateChart();
  },
  { deep: true }
);

onMounted(() => {
  initChart();
  window.addEventListener("resize", () => {
    chartInstance?.resize();
  });
});
</script>

<style scoped>
.health-chart-container {
  width: 100%;
  height: 800px;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.chart {
  width: 100%;
  height: 100%;
}
</style>
