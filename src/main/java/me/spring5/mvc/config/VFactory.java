package me.spring5.mvc.config;

import lombok.extern.log4j.Log4j;
import me.spring5.mvc.vo.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/***
 * @ Author: minikuma
 * @ Date: 19/02/07
 * @ Version: 1.0
 * @ Commenct:
 *      - Bean 에서 관리하는 Context
 *      - Type Conversion (A Object -> B Object)
 *        Factory Pattern 구현 가능하지만 Type Conversion으로 구현
 *      - Type Conversion Singleton이고 외부에서 호출되는 메소드의 Parameter Type에 맞게 Object Creatrion
 */

@Log4j
@Component
public class VFactory implements ConverterFactory<String, AbstractEntity> {

    @Override
    public <T extends AbstractEntity> Converter<String, T> getConverter(Class<T> targetClass) {
        return new VConverter(targetClass);
    }

    private static class VConverter<T extends AbstractEntity> implements Converter<String, T> {
        private Class<T> targetClass;

        public VConverter(Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public T convert(String targetStr) {
            if (this.targetClass == BM_COM_GETAPRVNO_OUTSTR.class) {
                log.debug("targetClass Create : " + BM_COM_GETAPRVNO_OUTSTR.class.getTypeName());
                return (T) new BM_COM_GETAPRVNO_OUTSTR();
            }
            return null;
        }
    }
}
