##Release Notes
##0.0.2-SNAPSHOT

- Enabled CORS for localhost:3000
  ##0.0.1-SNAPSHOT
- Heroku changes:
  - Configured product with Jhipster for Heroku
  - SMTP configuration (Elastic account, changed baseurl, changed system email)
  - Added api-docs to Procfile
- Added authorities to AuthoritiesConstants for using with CRUD
  - ROLE_PERMISSION_CREATE
  - ROLE_PERMISSION_UPDATE
  - ROLE_PERMISSION_READ
  - ROLE_PERMISSION_DELETE
- JDL updated to V4. Project updated.
- Updated base64-secret to match Core Platform
- Changed authorize method in UserJWTController to authenticate using Core Platform.
