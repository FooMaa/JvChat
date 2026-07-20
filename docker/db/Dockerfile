FROM ubuntu:24.04

WORKDIR /opt/JvChat/scripts/db

RUN apt-get update && \
    apt-get install -y \
    postgresql \
    python3 \
    sudo \
    python3-psycopg2 && \
    rm -rf /var/lib/apt/lists/*

COPY scripts/db /opt/JvChat/scripts/db
COPY data/pg_hba.conf /opt/JvChat/data/pg_hba.conf

EXPOSE 5432

ENTRYPOINT ["/opt/JvChat/scripts/db/make_default_db.sh"]