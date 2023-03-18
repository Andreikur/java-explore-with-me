package ru.practicum.viewStats.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "view_stats", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewStats {
    @Id
    @Column(name = "app_view")
    private String app;
    @Column(name = "uri_view")
    private String uri;
    @Column
    @PositiveOrZero
    private int hits;
}
