package org.snowcamp.university.springmodulith.persistence;

import org.springframework.data.annotation.Id;

public record CommandData(@Id String id , String name) {
}
