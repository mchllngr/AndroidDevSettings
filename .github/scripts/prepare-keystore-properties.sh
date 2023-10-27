#!/bin/sh

[ -d "$HOME"/secrets ] || mkdir "$HOME"/secrets

# Create the encrypted keystore
echo "$KEYSTORE" | base64 -di > keystore.jks.gpg

# Decrypt the keystore
# --batch to prevent interactive command
# --yes to assume "yes" for questions
gpg \
  --quiet \
  --batch \
  --yes \
  --decrypt \
  --passphrase="$KEYSTORE_ENCRYPTION_PASSPHRASE" \
  --output "$HOME"/secrets/keystore.jks keystore.jks.gpg

# Create keystore.properties
{
    echo "storeFile=$(realpath "$HOME"/secrets/keystore.jks)"
    echo "storePassword=$KEYSTORE_STORE_PASSPHRASE"
    echo "keyAlias=$KEYSTORE_ALIAS"
    echo "keyPassword=$KEYSTORE_ALIAS_PASSPHRASE"
} >> keystore.properties
