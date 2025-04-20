## 🐬 MySQL 테스트 환경 (Docker Compose)

이 프로젝트는 로컬 개발 및 테스트를 위해 Docker Compose 기반의 MySQL 8.0.36 환경을 제공합니다.

### 📦 구성 정보

- **MySQL 버전**: 8.0.36
- **포트**: 3306
- **DB 이름**: `bucket`
- **계정**: `root` / `12345`

### 🚀 실행 방법

```bash
# Docker Compose로 MySQL 컨테이너 실행
docker compose -f docker-compose.mysql.yml up -d