package stu.napls.boostimcenter.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author Tei Michael
 * @Date 12/29/2019
 */
@Component
@Getter
public class AuthServer {
    @Value("${authserver.url}")
    private String url;

}
