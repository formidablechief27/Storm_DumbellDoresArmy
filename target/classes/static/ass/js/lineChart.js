var data = {
    labels: ["2020", "2021", "2022", "2023"],
    datasets: [{
      label: "Likes",
      borderColor: "#FF5733",
      backgroundColor: "rgba(255, 87, 51, 0.2)",
      pointBackgroundColor: "#FF5733",
      pointBorderColor: "#FF5733",
      pointHoverBackgroundColor: "#FF5733",
      pointHoverBorderColor: "#FF5733",
      data: [100, 90, 200, 250], 
      fill: true,
    }]
  };
  
  var ctx = document.getElementById("likes-chart").getContext("2d");
  var likesChart = new Chart(ctx, {
    type: "line",
    data: data,
    options: {
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            stepSize: 50, 
          },
        },
      },
    },
  });
  console.log("working??");  
//   var legend = document.getElementById("likes-chart-legend");
//   legend.innerHTML = likesChart.generateLegend();
  