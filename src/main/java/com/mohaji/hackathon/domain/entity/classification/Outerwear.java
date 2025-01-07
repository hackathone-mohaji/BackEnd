package com.mohaji.hackathon.domain.entity.classification;

import com.mohaji.hackathon.common.enums.Attributes.Category;
import com.mohaji.hackathon.common.enums.Attributes.Color;
import com.mohaji.hackathon.common.enums.Attributes.Detail;
import com.mohaji.hackathon.common.enums.Attributes.Fit;
import com.mohaji.hackathon.common.enums.Attributes.Matter;
import com.mohaji.hackathon.common.enums.Attributes.Print;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Outerwear extends Classification {

}
