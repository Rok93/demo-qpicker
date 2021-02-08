package org.zeorck.sample;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ToString
@Data
public class Restaurant {

    private final Chef chef; // 책 예제에서는 @Setter(onMethod_ = @__({ Autowired })) 쓴다.
    // @Setter는 자동으로 setChef()를 컴파일 시 생성한다.
    // 필드의 Injection은 스프링에서 권장되지 않는다!

}
