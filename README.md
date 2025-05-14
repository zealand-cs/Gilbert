# Gilbert

A "Vinted" successor with a focus on quality and sustainability.

## Development

To start developing, run the docker-compose file:

```bash
docker compose up -d
```

Set policy for Minio bucket

```bash
docker exec <CONTAINER_ID> mc alias set local http://localhost:9000/ root rootpass
docker exec <CONTAINER_ID> mc anonymous set public local/gilbert
```

To get <CONTAINER_ID>:

```bash
docker ps
```
