package expeditors.backend.adoption.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class UriCreator {
    public URI getURI(int id) {
        URI newResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return newResource;
    }

    public URI getURI() {
        URI newResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        return newResource;
    }
}
