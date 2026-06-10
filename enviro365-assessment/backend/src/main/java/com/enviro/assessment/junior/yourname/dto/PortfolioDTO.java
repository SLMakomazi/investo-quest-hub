package com.enviro.assessment.junior.yourname.dto;

import java.math.BigDecimal;
import java.util.List;

public class PortfolioDTO {
    public Long id;
    public String name;
    public InvestorDTO investor;
    public List<ProductDTO> products;

    public static class ProductDTO {
        public Long id;
        public String type;
        public String name;
        public BigDecimal balance;
    }
}
