# Support Advice Response

## Exception
- 동작 시 @EnableException 어노테이션 호출 필요
## Response

### servlet
- 동작 시 @EnableServletResponse 어노테이션 호출 필요
- @EnableFlux 호출 시 에러 발생
### reactive
- 동작 시 @EnableReactiveResponse 어노테이션 호출 필요
- @EnableWebMvc
- application.properties 내용 추가 spring.main.allow-bean-definition-overriding=true
- application.properties 내용 추가  spring.main.web-application-type=REACTIVE