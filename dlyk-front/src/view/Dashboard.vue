<script setup>
import { ref } from 'vue'
import { getSalesFunnelDataApi, getSummaryDataApi } from '../api/dashboard.js'
import * as echarts from 'echarts'

// 概述统计数据
const summaryData = ref({})
const loadSummary = async () => {
  const { data } = await getSummaryDataApi()
  summaryData.value = data.data
}
loadSummary()

// 渲染销售漏斗图
const loadSaleFunnelChart = async () => {
  const { data } = await getSalesFunnelDataApi()

  const funnelDom = document.getElementById('saleFunnelChart')
  const funnelChart = echarts.init(funnelDom)
  const option = {
    title: {
      text: '销售漏斗图',
      left: 'center',
      top: 'bottom'
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c}'
    },
    /*toolbox: {
      feature: {
        dataView: {
          readOnly: false
        },
        restore: {},
        saveAsImage: {}
      }
    },*/
    legend: {
      data: ['线索', '客户', '交易', '成交']
    },
    series: [
      {
        name: '销售漏斗数据统计',
        type: 'funnel',
        left: '10%',
        top: 60,
        bottom: 60,
        width: '80%',
        min: 0,
        max: 100,
        minSize: '0%',
        maxSize: '100%',
        sort: 'descending',
        gap: 2,
        label: {
          show: true,
          position: 'inside'
        },
        labelLine: {
          length: 10,
          lineStyle: {
            width: 1,
            type: 'solid'
          }
        },
        itemStyle: {
          borderColor: '#fff',
          borderWidth: 1
        },
        emphasis: {
          label: {
            fontSize: 20
          }
        },
        data: data.data
      }
    ]
  }
  funnelChart.setOption(option)
}
loadSaleFunnelChart()

// 渲染线索来源饼图
const loadClueSourcePieChart = async () => {
  const { data } = await getSalesFunnelDataApi()
  const clueSourcePieDom = document.getElementById('sourcePieChart')
  const clueSourcePieChart = echarts.init(clueSourcePieDom)
  const option = {
    title: {
      text: '线索来源统计',
      left: 'center',
      top: 'bottom'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'horizontal',
      left: 'center'
    },
    series: [
      {
        name: '线索来源统计',
        type: 'pie',
        //饼图的半径
        radius: '60%',
        data: data.data,
        //高亮状态的扇区和标签样式。
        emphasis: {
          itemStyle: {
            //图形阴影的模糊大小
            shadowBlur: 10,
            //阴影水平方向上的偏移距离。
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
  clueSourcePieChart.setOption(option)
}
loadClueSourcePieChart()

</script>

<template>
  <!--概览统计-->
  <el-row>
    <el-col :span="6">
      <el-statistic :value="summaryData.effectiveActivityCount">
        <template #title>
          <div style="display: inline-flex; align-items: center">
            市场活动
          </div>
        </template>
        <template #suffix>/{{ summaryData.totalActivityCount }}</template>
      </el-statistic>
    </el-col>

    <el-col :span="6">
      <el-statistic title="线索总数" :value="summaryData.totalClueCount"/>
    </el-col>

    <el-col :span="6">
      <el-statistic title="客户总数" :value="summaryData.totalCustomerCount"/>
    </el-col>

    <el-col :span="6">
      <el-statistic :value="summaryData.successTranAmount">
        <template #title>
          <div style="display: inline-flex; align-items: center">
            交易总额
          </div>
        </template>
        <template #suffix>/{{ summaryData.totalTranAmount }}</template>
      </el-statistic>
    </el-col>
  </el-row>

  <div class="first-line">
    <!-- 销售漏斗图，为 ECharts 准备一个定义了宽高的 DOM -->
    <div id="saleFunnelChart"></div>

    <!-- 线索来源饼图，为 ECharts 准备一个定义了宽高的 DOM -->
    <div id="sourcePieChart"></div>
  </div>

</template>

<style lang="scss" scoped>
.el-row {
  text-align: center;
}
.first-line {
  margin-top: 30px;
  display: flex;
  div {
    flex: 1;
    height: 400px;
  }
}
</style>