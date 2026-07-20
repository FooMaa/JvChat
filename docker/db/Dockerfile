FROM postgres:17

WORKDIR /opt/db

RUN apt update && \
    apt install -y python3 python3-psycopg2

COPY scripts/db /opt/db

RUN cat > /opt/db/init.sh <<'EOF'
#!/bin/bash
set -e
cd /opt/db
./make_default_db.sh
EOF

RUN chmod a+x /opt/db/init.sh && \
    cp /opt/db/init.sh /docker-entrypoint-initdb.d/init.sh