const getOptionChart1 = ()=>{
    return{
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sund']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar'
          }
        ]
      };
};
const initCharts = () =>{
  const chart1 = echarts.init(document.getElementById("chart1"));
  chart1.setOption(getOptionChart1());
};
window.addEventListener("load", ()=>{
    initCharts(); 
});

