package com.sfelaco.rsocket.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.codec.CharSequenceEncoder;
import org.springframework.core.codec.StringDecoder;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;

@Configuration
public class RSocketConfiguration {


	
	 @Bean
	 public RSocket rSocket() {
	        return RSocketFactory
	                .connect()
	                .metadataMimeType("message/x.rsocket.composite-metadata.v0")
	                .frameDecoder(PayloadDecoder.ZERO_COPY)
	                .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
	                .transport(TcpClientTransport.create(7000))
	                .start()
	                .block();
	    }
	 
//	 @Bean
//	 public RSocketStrategies rsocketStrategies() {
//	     return RSocketStrategies.builder()
//	         .decoder(StringDecoder.textPlainOnly())
//	         .encoder(CharSequenceEncoder.allMimeTypes())
//	         .dataBufferFactory(new DefaultDataBufferFactory(true))
//	         .build();
//	 }

	    @Bean
	    public RSocketRequester rSocketRequester(RSocket rSocket, RSocketStrategies rSocketStrategies) {
	        return RSocketRequester.wrap(rSocket, MimeTypeUtils.APPLICATION_JSON,
	        		MimeTypeUtils.parseMimeType("message/x.rsocket.composite-metadata.v0"),
	                rSocketStrategies);
	    }
	
}
