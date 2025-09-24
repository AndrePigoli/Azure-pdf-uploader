📚 Projeto PDF Blob Storage + RAG com Qdrant
🚀 Visão Geral

Este projeto tem como objetivo construir uma plataforma de upload e processamento de PDFs, integrando Azure Blob Storage com um pipeline de RAG (Retrieval Augmented Generation) para consultas inteligentes a documentos.

Atualmente, o projeto já conta com um serviço em Spring Boot que:

Expõe um endpoint REST para upload de PDFs.

Armazena os PDFs diretamente no Azure Blob Storage.

Mantém os metadados dos arquivos em um banco relacional.

Implementa autenticação com JWT para proteger endpoints (apenas usuários logados podem fazer upload).

📂 Estrutura Atual

Backend (Spring Boot)

PdfController: controla endpoints relacionados a upload de PDF.

AzureBlobPdfService: responsável por enviar os PDFs ao Blob Storage.

JwtAuth: camada de segurança via token JWT.

AdminRepository: persistência do usuário admin no banco relacional, com senha criptografada (BCrypt).

Front-end (em desenvolvimento)

Área de login (JWT + verificação anti-bot).

Área de upload de PDFs, que envia para o endpoint /api-blob/pdfs/upload.

Área futura para chat com IA (consulta ao RAG).

🧠 Próximos Passos: Integração com RAG

O objetivo final é transformar este projeto em uma plataforma de consulta a PDFs com IA. O fluxo será:

Upload de PDF → Enviado ao Azure Blob Storage.

Ingestão automática → Um microserviço consumirá eventos do Event Grid sempre que um novo PDF for adicionado.

Processamento

Download do PDF.

Extração do texto.

Geração de chunks.

Criação de embeddings vetoriais.

Armazenamento no banco vetorial → Os embeddings serão salvos no Qdrant.

Consulta via IA →

Usuário envia uma pergunta no front-end.

O sistema consulta o Qdrant para buscar os chunks relevantes.

O contexto é enviado para um modelo de linguagem (LLM) via LangChain ou Agno.

O modelo responde com base nos documentos.

🔧 Tecnologias Utilizadas

Spring Boot (Java) – Backend e API de upload

Azure Blob Storage – Armazenamento dos PDFs

Azure Event Grid – Disparo de eventos para processamento automático

JWT (JSON Web Token) – Autenticação

BCrypt – Criptografia de senhas

Qdrant – Banco de vetores para embeddings

LangChain / Agno (Python) – Orquestração do RAG

Front-end moderno (React + Tailwind) – Interface de upload e consulta

📌 Roadmap

 Criar API para upload de PDFs (Spring Boot + Blob Storage).

 Adicionar autenticação JWT.

 Finalizar front-end (login + upload).

 Criar microserviço de ingestão com Event Grid ou utilizando WebHook.

 Processar PDFs (chunks + embeddings).

 Salvar embeddings no Qdrant.

 Criar chat de consulta usando LangChain/Agno.
