package com.report.configuration;


import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableReactiveCassandraRepositories(basePackages = "com.report")
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {

    @Value("${cassandra.contactpoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.keyspace}")
    private String keyspace;


    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public CassandraClusterFactoryBean cluster() {


        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();


        cluster.setJmxReportingEnabled(false);
        cluster.setContactPoints(contactPoints);
        cluster.setPort(port);
        cluster.setKeyspaceCreations(getKeyspaceCreations());
        cluster.setReconnectionPolicy(new ConstantReconnectionPolicy(1000));

        return cluster;
    }


    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keyspace)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return List.of(specification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return List.of(DropKeyspaceSpecification.dropKeyspace(keyspace));
    }

    @Override
    protected List<String> getStartupScripts() {
        return Arrays.asList("CREATE TABLE IF NOT EXISTS " + keyspace + ".person(id text, first_name text,last_name text, birth date, PRIMARY KEY(id) ) ",
                "CREATE TABLE IF NOT EXISTS " + keyspace + ".image_room(room_number text, year_month text, content blob, PRIMARY KEY(room_number, year_month)) ",
                "CREATE TABLE IF NOT EXISTS " + keyspace +
                        ".classroom(id text , year_month text, capacity int, room_number text, description text, PRIMARY KEY (id, year_month))",
                "CREATE TABLE IF NOT EXISTS " + keyspace + ".classroom_kids (id_classroom text, snapshot_date date, person_id text, PRIMARY KEY(id_classroom, snapshot_date))  WITH CLUSTERING ORDER BY (snapshot DESC); ",
                "CREATE TABLE IF NOT EXISTS " + keyspace + ".classroom_changes (id_classroom text, change date, PRIMARY KEY(id_classroom)) "
        );
    }

//    @Override
//    protected List<String> getShutdownScripts() {
//        return List.of("DROP KEYSPACE IF EXISTS " + keyspace + ";");
//    }

//    @Bean
//    CassandraCustomConversions cassandraCustomConversions() {
//        List<Converter<?, ?>> converters = new ArrayList<>();
//        converters.add(new CounterDataReadConverter());
//        return new CassandraCustomConversions(converters);
//    }
//
//    static class CounterDataReadConverter implements Converter<ByteBuffer, MyData> {
//        @Override
//        public MyData convert(ByteBuffer source) {
//            try {
//                return MyData.parseFrom(source.array());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
}
