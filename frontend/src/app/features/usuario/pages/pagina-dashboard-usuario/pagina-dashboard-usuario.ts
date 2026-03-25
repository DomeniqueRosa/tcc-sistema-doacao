import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { ApexAxisChartSeries, ApexChart, ApexDataLabels, ApexLegend, ApexNonAxisChartSeries, ApexPlotOptions, ApexResponsive, ApexStroke,
ApexTitleSubtitle, ApexXAxis, ApexYAxis, ChartComponent, NgApexchartsModule } from 'ng-apexcharts';
import { MatCardModule } from '@angular/material/card';

export type DonutChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  labels: string[];
  responsive: ApexResponsive[];
  legend: ApexLegend;
  dataLabels: ApexDataLabels;
  stroke: ApexStroke;
  title: ApexTitleSubtitle;
};

export type BarChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  yaxis: ApexYAxis;
  dataLabels: ApexDataLabels;
  plotOptions: ApexPlotOptions;
  title: ApexTitleSubtitle;
};

@Component({
  selector: 'app-pagina-dashboard-usuario',
  standalone: true,
  imports: [CommonModule, MatCardModule, NgApexchartsModule],
  templateUrl: './pagina-dashboard-usuario.html',
  styleUrl: './pagina-dashboard-usuario.css'
})
export class PaginaDashboardUsuario {
  @ViewChild('donutChart') donutChart!: ChartComponent;
  @ViewChild('barChart') barChart!: ChartComponent;

  // pegar nome do usuário autenticado
  nomeUsuario = 'Usuário';

  // vir da API
  totalDoacoes = 0;
  totalAlunosBeneficiados = 0;

  public donutChartOptions: DonutChartOptions = {
    series: [0, 0],
    chart: {
      type: 'donut',
      height: 280
    },
    labels: ['Computadores', 'Notebooks'],
    responsive: [
      {
        breakpoint: 768,
        options: {
          chart: {
            width: 260
          },
          legend: {
            position: 'bottom'
          }
        }
      }
    ],
    legend: {
      position: 'right'
    },
    dataLabels: {
      enabled: true
    },
    stroke: {
      width: 1
    },
    title: {
      text: 'Equipamentos'
    }
  };

  public barChartOptions: BarChartOptions = {
    series: [
      {
        name: 'Alunos',
        data: [0, 0, 0, 0, 0, 0]
      }
    ],
    chart: {
      type: 'bar',
      height: 280
    },
    xaxis: {
      categories: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun']
    },
    yaxis: {
      title: {
        text: 'Quantidade'
      }
    },
    dataLabels: {
      enabled: false
    },
    plotOptions: {
      bar: {
        borderRadius: 6
      }
    },
    title: {
      text: 'Beneficiados'
    }
  };

  constructor() {
    // chamar API do usuário
  }
}