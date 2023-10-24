package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.repository.AsientoRepository;
import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.IAsientoService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@Controller
public class HistogramController {
    AsientoRepository asientoRepository;
    @Autowired
    private IAsientoService asientoService;

    @GetMapping("/histogram")
    public void generateHistogram(HttpServletResponse response) throws Exception {
        // Obtén la cantidad de asientos creados en el mes actual (debes implementar este método)
        int asientosEnElMes = asientoService.contarAsientosEnElMesActual(); // Implementa este método en tu servicio

        // Crea un conjunto de datos para el histograma
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("Asientos en el mes", new double[] { asientosEnElMes }, 1); // Un solo valor para el mes

        // Crea el gráfico de histograma
        JFreeChart chart = ChartFactory.createHistogram("Histograma de Asientos en el Mes", "Mes", "Cantidad de Asientos", dataset);

        // Guarda el gráfico como una imagen y escríbela en la respuesta HTTP
        response.setContentType("image/png");
        ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 500, 300);
    }
}
