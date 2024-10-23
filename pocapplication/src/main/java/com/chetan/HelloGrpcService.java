package com.chetan;

import io.quarkus.grpc.GrpcService;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@GrpcService
public class HelloGrpcService extends MutinyHelloGrpcGrpc.HelloGrpcImplBase {
    Logger log = LoggerFactory.getLogger(HelloGrpcService.class);

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public Uni<PersonResponse> getPersonById(PersonRequest request) {
        System.out.println("jasgvhujqdvud"+request.getName());
        return Uni.createFrom().item(() -> {
            PersonEntity person = PersonEntity.findById(request.getId());
            if (person != null) {
                return personToResponse(person, "Person found");
            } else {
                return PersonResponse.newBuilder()
                        .setMessage("Person not found")
                        .build();
            }
        });
    }

    @Override
    @Transactional
    public Uni<PersonResponse> createPerson(PersonRequest request) {
        log.info("jasgvhujqdvud"+request.getName());
        return Uni.createFrom().item(() -> {
            PersonEntity person = new PersonEntity();
            person.name = request.getName();
            person.age = request.getAge();
            person.persist();
            return personToResponse(person, "Person created");
        });
    }

    private PersonResponse personToResponse(PersonEntity person, String message) {
        return PersonResponse.newBuilder()
                .setMessage(message)
                .build();
    }

}
