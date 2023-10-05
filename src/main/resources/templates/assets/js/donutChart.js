var data = {
    datasets: [{
      data: [25, 30, 10, 35],
      backgroundColor: [
        'rgba(153, 102, 255, 0.5)',
        'rgba(255, 159, 64, 0.5)',
        'rgba(255, 99, 132, 0.5)',
        'rgba(54, 162, 235, 0.5)',
      ],
      borderColor: [
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)',
        'rgba(255,99,132,1)',
        'rgba(54, 162, 235, 1)',
      ],
    }],
     labels: ["Deaf", "Blind", "Handicapped", "Down Syndrome"],
  };

  // Get the canvas element and create the donut chart
  var ctx = document.getElementById("traffic-chart-new").getContext("2d");
  var donutChart = new Chart(ctx, {
    type: "doughnut",
    data: data,
    options: {
      cutoutPercentage: 50,
    },
  });

//   // Optionally, you can create a legend
//   var legend = document.getElementById("traffic-chart-legend");
//   legend.innerHTML = donutChart.generateLegend();
