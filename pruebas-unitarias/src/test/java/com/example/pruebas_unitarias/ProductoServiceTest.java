package com.example.pruebas_unitarias;

import com.example.pruebas_unitarias.model.Producto;
import com.example.pruebas_unitarias.repository.ProductoRepository;
import com.example.pruebas_unitarias.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void findAll_debeRetornarListaDeProductos() {
        Producto p1 = new Producto(1L, "Teclado Mecánico", 1200.0);
        Producto p2 = new Producto(2L, "Mouse Inalámbrico", 650.0);
        when(productoRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Producto> resultado = productoService.findAll();

        assertThat(resultado)
                .isNotNull()
                .hasSize(2)
                .extracting(Producto::getNombre)
                .containsExactly("Teclado Mecánico", "Mouse Inalámbrico");

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void create_debeGuardarYRetornarProductoConAtributosValidados() {
        Producto nuevoProducto = new Producto(null, "Monitor 24\"", 3500.0);
        Producto productoGuardado = new Producto(10L, "Monitor 24\"", 3500.0);
        when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado);

        Producto creado = productoService.create(nuevoProducto);

        assertThat(creado).isNotNull();
        assertThat(creado.getId()).isEqualTo(10L);
        assertThat(creado.getNombre()).isEqualTo("Monitor 24\"");
        assertThat(creado.getPrecio()).isEqualTo(3500.0);

        verify(productoRepository, times(1)).save(nuevoProducto);
    }
}