**Sistema de Gestão Hospitalar e de Serviços de Saúde (SGHSS)**

Este repositório contém o modelo de dados e a estrutura básica para um sistema de gestão hospitalar e de serviços de saúde, incluindo funcionalidades para agendamento de consultas, registros médicos eletrônicos, prescrições digitais, teleconsultas, gerenciamento de pacientes e profissionais, entre outros.

- Organization (unidade hospitalar / clínica)
- User (conta de acesso) — base para Paciente, Profissional e Administrador
- Patient (perfil do paciente)
- Professional (perfil do profissional)
- Role / Permission (controle de acesso)
- Appointment (consulta / agendamento)
- Encounter (registro clínico; cada encontro)
- Prescription (prescrição digital)
- TeleconsultationSession (sessão WebRTC)
- TeleconsultationRecording (metadados da gravação armazenada em objeto)