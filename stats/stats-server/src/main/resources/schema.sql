--drop table endpoint_hit;
--drop table view_stats;

CREATE TABLE IF NOT EXISTS endpoint_hit (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    app VARCHAR(255) NOT NULL,
    uri VARCHAR(255) NOT NULL,
    ip VARCHAR(20) NOT NULL,
    timestamp_endpoint TIMESTAMP WITHOUT TIME ZONE not null
);
