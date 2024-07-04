const getOptionChart1 = ()=>{
    return{
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [150, 230, 224, 218, 135, 147, 260],
            type: 'line'
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

