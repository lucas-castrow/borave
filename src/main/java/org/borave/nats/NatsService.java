package org.borave.nats;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import io.nats.client.ConnectionListener;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.borave.config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NatsService {
    private final String natsServerUrl;
    private final JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public NatsService(@Value("${nats.server.url}") String natsServerUrl,
                       JwtTokenProvider jwtTokenProvider) {
        this.natsServerUrl = natsServerUrl;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void publish(String subject, Object data) {
        try {
            Options options = new Options.Builder()
                    .server(natsServerUrl)
                    .build();
            String message = objectMapper.writeValueAsString(data);
            Connection connection = Nats.connect(options);
            connection.publish(subject, message.getBytes());
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
