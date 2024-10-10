Sistema de Gestão de Caixa para loja de eletrônicos

1. Objetivo do Projeto
O projeto visa desenvolver um sistema de gestão de caixa para uma loja de eletrônicos, utilizando Java Maven para a estruturação do projeto, JDBC para a integração com o banco de dados e Java Swing para a construção de uma interface gráfica intuitiva. O sistema deve ser capaz de gerenciar vendas, controle de estoque de produtos, cadastro de clientes e fornecedores, e registrar de forma detalhada todas as transações, visando otimizar a administração da loja.

2. Funcionalidades Principais

2.1 Sistema de Caixa
Registro de Vendas:
Implementação de um sistema de registro de vendas que calcula automaticamente o total da compra, incluindo descontos e taxas.
Integração de clientes às vendas, permitindo a visualização do histórico de compras para cada cliente.
Suporte a múltiplas formas de pagamento (cartão de crédito, débito, dinheiro, etc.) com opções para parcelamento.
Registro detalhado de produtos vendidos, incluindo quantidades e valores unitários.

2.2 Gerenciamento de Estoque
Cadastro e Controle de Produtos:
Cadastro de produtos com informações detalhadas: nome, preço, quantidade, fornecedor, e categoria do produto.
Implementação de um controle de estoque que realiza baixas automáticas conforme as vendas são realizadas, emitindo alertas para reposição de produtos em baixo estoque.
Listagem, atualização e pesquisa avançada de produtos, com filtros por categoria e fornecedor.

2.3 Cadastro de Clientes e Fornecedores
Gerenciamento de Cadastros:
Cadastro de novos clientes com informações completas (nome, telefone, e-mail, endereço) e histórico de compras.
Cadastro de fornecedores, incluindo informações relevantes para o relacionamento comercial, como prazo de entrega e condições de pagamento.
Funcionalidade de atualização e exclusão de clientes e fornecedores, com validações para evitar a remoção de registros vinculados a transações.

2.4 Relatórios e Consultas
Consultas Avançadas:
Consulta de vendas por data, cliente ou produto, com geração de relatórios em formatos exportáveis (PDF, Excel).
Relatórios detalhados de vendas diárias, incluindo gráficos de desempenho por período.
Relatórios de produtos em estoque, destacando aqueles com baixa quantidade e que necessitam de reposição.
Histórico de compras por cliente, permitindo ações de marketing direcionadas.

3. Tecnologias Utilizadas
Linguagem: Java 17 ou superior, visando aproveitar as últimas melhorias de desempenho e segurança.
Frameworks: Maven para gerenciamento de dependências e estruturação do projeto, garantindo um ambiente modular e escalável.
Interface Gráfica: Java Swing, utilizando componentes avançados como JTabbedPane, JTable, JComboBox e JOptionPane para criar uma experiência de usuário interativa e fluida.
Banco de Dados: PostgreSQL via JDBC para conexão com o banco de dados, com a implementação de práticas de segurança para proteger os dados dos clientes e transações.
Padrão de Projeto: DAO (Data Access Object) para facilitar o acesso e a manipulação dos dados no banco, promovendo uma separação clara entre a lógica de negócio e a persistência.
Estrutura de Pacotes: Organização de pacotes dividida por funcionalidades, como model, dao, service, controller e view, promovendo a manutenibilidade e escalabilidade do código.

4. Estrutura de Classes
Cliente:
Atributos: id, nome, telefone, email, endereco, historicoCompras
Fornecedor:
Atributos: id, nome, produto_fornecido, contato, condicoesPagamento
Produto:
Atributos: id, nome, preco, quantidade, fornecedor, categoria
Venda:
Atributos: id, data, total, formaPagamento, clienteId, produtosVendidos
VendaDAO: Métodos de CRUD (Create, Read, Update, Delete) para vendas e itens de venda, incluindo consultas complexas.
ProdutoDAO: Métodos para gerenciar o estoque e produtos, com funcionalidades para relatórios e controle de baixa automática.

5. Cronograma
Fase	Descrição	Tempo Estimado

1. Análise de Requisitos	Levantamento de requisitos e análise funcional do sistema.	1 semana

2. Design do Sistema	Estruturação da interface gráfica e dos modelos de dados.	1 semana

3. Implementação do CRUD	Implementação do cadastro e gerenciamento de Clientes, Fornecedores e Produtos.	2 semanas

4. Sistema de Vendas	Desenvolvimento das funcionalidades de vendas e baixa de produtos no estoque.	3 semanas

5. Relatórios e Consultas	Implementação das consultas e geração de relatórios de vendas e estoque.	1 semana

6. Testes e Validação	Testes do sistema para garantir o funcionamento correto das funcionalidades.	1 semana

7. Documentação e Treinamento	Criação da documentação do sistema e treinamento dos usuários.	1 semana

6. Riscos
Erros na Integração com o Banco de Dados: O uso de JDBC requer uma atenção rigorosa à manipulação de conexões e consultas SQL. Implementar um sistema de tratamento de exceções robusto para evitar falhas de conexão e garantir a integridade dos dados.
Interface Amigável: O desafio de criar uma interface gráfica intuitiva e responsiva que atenda às necessidades dos usuários finais.
Gestão de Estoque: Garantir que o controle de estoque funcione corretamente com a baixa automática ao registrar vendas. Incluir testes para simular cenários de vendas e garantir a precisão dos dados.

7. Recursos Necessários
Desenvolvedores: 1-2 desenvolvedores com experiência em Java, JDBC e design de interfaces gráficas.
Ferramentas: IDEs (IntelliJ ou Eclipse) e um banco de dados relacional como PostgreSQL ou MySQL para armazenamento dos dados.
Bibliotecas: Maven para gerenciamento de dependências e bibliotecas Java Swing para desenvolvimento da interface gráfica.

8. Conclusão
O projeto visa entregar um sistema completo e eficiente para o gerenciamento de vendas e estoque de um loja de eletrônicos. A solução deve proporcionar uma visão abrangente dos produtos, das vendas diárias e do histórico de clientes, facilitando a organização e eficiência do negócio. O sistema deverá ser flexível o suficiente para se adaptar ao crescimento da loja, incorporando novas funcionalidades conforme necessário.

Manual do Usuário: Sistema de Gestão de Caixa para loja de eletrônicos
1. Introdução
Este manual foi criado para auxiliar os usuários na operação do sistema de gestão de caixa desenvolvido para um loja de eletrônicos, com funcionalidades que abrangem desde o registro de vendas até o gerenciamento de estoque, cadastros de clientes e fornecedores.

2. Acesso ao Sistema

2.1 Abrindo o Sistema
Ao iniciar o sistema, a janela principal será exibida, apresentando um JTabbedPane com as seguintes abas:
Clientes
Fornecedores
Produtos
Vendas
Cada aba contém funcionalidades específicas para cadastro, listagem e gerenciamento das informações.

3. Cadastro de Clientes
3.1 Como cadastrar um cliente
Clique na aba Clientes.
Preencha os campos obrigatórios: Nome, Telefone, E-mail e, opcionalmente, Endereço.
Clique no botão Salvar para registrar o cliente.

3.2 Como visualizar e editar clientes
Na aba Clientes, clique em Listar Clientes para exibir a lista.
Selecione um cliente da lista e clique em Editar para modificar as informações desejadas.
Após realizar as alterações, clique em Salvar Alterações.

3.3 Como excluir um cliente
Na aba Clientes, selecione o cliente que deseja excluir.
Clique no botão Excluir. Uma confirmação será solicitada para evitar exclusões acidentais.

4. Cadastro de Fornecedores

4.1 Como cadastrar um fornecedor
Navegue até a aba Fornecedores.
Preencha os campos obrigatórios: Nome, Produto Fornecido e Contato.
Clique em Salvar para adicionar o fornecedor ao sistema.

4.2 Como visualizar, editar e excluir fornecedores
As funcionalidades são semelhantes às descritas na seção de clientes:
Para visualizar: clique em Listar Fornecedores.
Para editar: selecione um fornecedor, faça as modificações necessárias e clique em Salvar Alterações.
Para excluir: selecione o fornecedor desejado e clique em Excluir, confirmando a ação.

5. Gerenciamento de Produtos

5.1 Como cadastrar um produto
Acesse a aba Produtos.
Preencha as informações do produto: Nome, Preço, Quantidade e Fornecedor.
Clique em Salvar para adicionar o produto ao estoque.

5.2 Consultas e edição de produtos
Utilize o botão Listar Produtos para exibir todos os itens cadastrados.
Para editar um produto, selecione-o na lista e clique em Editar.
Após as alterações, clique em Salvar Alterações.

5.3 Como excluir um produto
Selecione o produto e clique em Excluir, confirmando a ação.

6. Registro de Vendas

6.1 Como registrar uma venda
Acesse a aba Vendas.
Adicione os produtos ao carrinho de vendas, selecionando o cliente e os produtos desejados.
Após adicionar todos os produtos, clique em Finalizar Venda para registrar a transação.

6.2 Como consultar vendas
Utilize o botão Consultar Vendas para gerar relatórios com filtros por data, cliente ou produto.

7. Conclusão
O sistema de gestão de caixa para loja de eletrônicos foi projetado para facilitar o dia a dia da loja, oferecendo ferramentas eficazes para o controle de vendas e estoque. Em caso de dúvidas ou dificuldades, entre em contato com o suporte técnico.

## Diagrama de Classe

![alt text](img/DC.png)

## Diagrama de Fluxo

![alt text](img/DF.png)