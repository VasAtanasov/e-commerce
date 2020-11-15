package org.atanasov.gamestore.core.domain.products;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "game")
public class Game extends Product {

  @Min(1)
  @Column(name = "size")
  private long size;

  @Column(name = "release_date")
  private LocalDate releaseDate;

  @Column(name = "youtube_id")
  private String youtubeId;
}
