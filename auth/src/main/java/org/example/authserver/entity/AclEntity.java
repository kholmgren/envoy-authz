package org.example.authserver.entity;

import authserver.acl.Acl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Log
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "acls")
public class AclEntity implements Serializable {

    @Id
    @PrimaryKeyColumn(
            name = "id",
            ordinal = 2,
            type = PrimaryKeyType.PARTITIONED,
            ordering = Ordering.DESCENDING)
    @Builder.Default
    private UUID id = UUID.randomUUID();
    @Indexed
    @Column
    private String namespace;
    @Indexed
    @Column
    private String object;
    private String relation;
    @Indexed
    @Column
    private String user;

    private String usersetNamespace;
    private String usersetObject;
    private String usersetRelation;

    @Builder.Default
    private Long created = System.currentTimeMillis();
    @Builder.Default
    private Long updated = System.currentTimeMillis();

    public Acl toAcl() {
        return Acl.builder()
                .namespace(namespace)
                .object(object)
                .relation(relation)
                .user(user)
                .usersetNamespace(usersetNamespace)
                .usersetObject(usersetObject)
                .usersetRelation(usersetRelation)
                .build();
    }

}
