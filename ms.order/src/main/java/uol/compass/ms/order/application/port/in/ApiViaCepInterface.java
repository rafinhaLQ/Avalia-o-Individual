package uol.compass.ms.order.application.port.in;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uol.compass.ms.order.domain.dto.response.ApiViaCepResponseDTO;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface ApiViaCepInterface {
    @GetMapping("{cep}/json")
    ApiViaCepResponseDTO findAddressWithCep(@PathVariable("cep") String cep);
}
