package com.Factura.factura.service;

import org.springframework.stereotype.Service;
import com.Factura.factura.client.CarritoFeignClient;
import com.Factura.factura.client.UsuarioFeignClient;
import com.Factura.factura.dto.CarritoDTO;
import com.Factura.factura.dto.FacturaCompletaDTO;
import com.Factura.factura.dto.UsuarioDTO;
import com.Factura.factura.model.FacturaModel;
import com.Factura.factura.repository.FacturaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final UsuarioFeignClient usuarioFeignClient;
    private final CarritoFeignClient carritoFeignClient;

    // Inyección limpia por constructor
    FacturaService(FacturaRepository facturaRepository,
            UsuarioFeignClient usuarioFeignClient,
            CarritoFeignClient carritoFeignClient) {
        this.facturaRepository = facturaRepository;
        this.usuarioFeignClient = usuarioFeignClient;
        this.carritoFeignClient = carritoFeignClient;
    }

    public FacturaModel crearFactura(FacturaModel factura) {
        return facturaRepository.save(factura);
    }

    public List<FacturaModel> obtenerTodas() {
        return facturaRepository.findAll();
    }

    public Optional<FacturaModel> obtenerPorId(Long id) {
        return facturaRepository.findById(id);
    }

    public FacturaModel actualizarFactura(Long id, FacturaModel facturaDetalles) {
        return facturaRepository.findById(id).map(factura -> {
            factura.setTotal(facturaDetalles.getTotal());
            factura.setFecha(facturaDetalles.getFecha());
            factura.setEstadoOrden(facturaDetalles.isEstadoOrden());
            factura.setIdPago(facturaDetalles.getIdPago());
            factura.setIdUsuario(facturaDetalles.getIdUsuario());
            factura.setIdCarrito(facturaDetalles.getIdCarrito());
            return facturaRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada con el id: " + id));
    }

    public void eliminarFactura(Long id) {
        if (facturaRepository.existsById(id)) {
            facturaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Factura no encontrada con el id: " + id);
        }
    }

    public List<FacturaModel> buscarPorFecha(Date fecha) {
        return facturaRepository.findByFecha(fecha);
    }

    public List<FacturaModel> buscarPorTotal(Integer total) {
        return facturaRepository.findByTotal(total);
    }

    // ⭐ NUEVO MÉTODO COMBINADO CON FEIGN
    public FacturaCompletaDTO obtenerFacturaCompleta(Long idFactura) {
        FacturaModel factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con el id: " + idFactura));

        UsuarioDTO usuario = null;
        try {
            usuario = usuarioFeignClient.obtenerUsuarioPorId(factura.getIdUsuario());
        } catch (Exception e) {
            System.err.println("No se pudo obtener el usuario: " + e.getMessage());
        }

        CarritoDTO carrito = null;
        try {
            carrito = carritoFeignClient.obtenerCarritoPorId(factura.getIdCarrito());
        } catch (Exception e) {
            System.err.println("No se pudo obtener el carrito: " + e.getMessage());
        }

        FacturaCompletaDTO respuesta = new FacturaCompletaDTO();
        respuesta.setFactura(factura);
        respuesta.setUsuario(usuario);
        respuesta.setCarrito(carrito);

        return respuesta;
    }
}