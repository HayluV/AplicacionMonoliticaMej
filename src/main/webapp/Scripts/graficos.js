 window.addEventListener("load", () => {
      initCharts();
      grafico1();
      grafico2();

});
grafico1();
grafico2();
    function grafico1(){
      $.ajax({
        type: "GET",
        data: {
          type: "6"
        },
        url: "ServletCliente",
        beforeSend: function () {
        },
        success: function (response) {
          console.log('contenido cantidad daSHBOARD', response);
          var cantidad = [];
          cantidad = parseInt(response.body);
          updateChart(cantidad);
          console.log('grafico cantidad', cantidad);
        },
        error: function (jqXHR, textStatus, errorThrown) {
          console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
        }
      });  
    }

    function grafico2(){
        $.ajax({
        type: "GET",
        data: {
          type: "10"
        },
        url: "ServletCliente",
        beforeSend: function () {
        },
        success: function (response) {
          console.log('contenido cantidad daSHBOARD 2:', response);
          let cantidad = [];
          let nombres = [];
          
          updateChart2(cantidad,nombres);
           for (var i = 0; i < response.body.length; i++) {
              cantidad = response.body[i].mntsaldo;
              nombres = response.body[i].descnombre;
           }
          //updateChart2(nombres);
          console.log('grafico cantidad2', cantidad);
          console.log('grafico nombres2', nombres);
        },
        error: function (jqXHR, textStatus, errorThrown) {
          console.error("Error en la solicitud AJAX: ", textStatus, errorThrown);
        }
      });
    }
    function getOptionChart1(cantidad) {
      return {
        xAxis: {
          type: 'category',
          data: ['Clientes'] // Etiquetas para los días de la semana
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [cantidad],
            type: 'bar'
          }
        ]
      };
    };
    
    function getOptionChart2(cantidad, nombres) {
      return  {
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: [nombres]
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [cantidad],
            type: 'line',
            areaStyle: {}
          }
        ]
      };
    }
    const initCharts = () => {
      const chart1 = echarts.init(document.getElementById("chart1"));
      const chart2 = echarts.init(document.getElementById("chart2"));
      chart1.setOption(getOptionChart1([])); 
      chart2.setOption(getOptionChart2([]));
      window.chart1 = chart1;
      window.chart2 = chart2;
    };

    const updateChart = (cantidad) => {
      const chart1 = window.chart1;
      const option = getOptionChart1(cantidad);
      chart1.setOption(option);
    };
    const updateChart2 = (cantidad,nombres) => {
      const chart2 = window.chart2;
      const option2 = getOptionChart2(cantidad,nombres);
      chart2.setOption(option2);
    };