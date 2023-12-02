# AutoBots - Sistema para gestão de lojas especializadas em manutenção veicular e vendas de autopeças. 

Desenvolvimento de um CRUD do cadastro das informações do cliente.
Nesta primeira etapa do projeto, o desenvolvimento segue os principais padrões de projeto e conceitos SOLID.

## :gear: Padrões de Projeto Aplicados

| Padrão de Projeto                   | Localização               | Descrição                                     |
| ----------------------------------- | ------------------------- | --------------------------------------------- |
| :hammer: Builder |   Post de Clientes                        | Os Clientes são construídos por várias entidades: Cliente, Documentos, Endereço e Telefones. Seria necessário implementar um construtor para cada rota post de Cliente.  |
| :dart: Strategy |   Todos os CRUDS                       |  Facilitar a flexibilidade, leitura e manutenção do código. |
| :page_facing_up: Template Method |    Seleção de Entidade por Id                       |  O código se repetia muitas vezes e para cada nova entidade de surgir e precise ser implementada, permitindo flexibilidade. |


## :railway_track: Rotas disponíveis

- http://localhost:8080/swagger-ui/
