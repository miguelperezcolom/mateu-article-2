docker login
docker buildx build --platform=linux/x86 -t miguelperezcolom/mateu-article2-api-gateway .
export VERSION=0.0.6
docker image tag miguelperezcolom/mateu-article2-api-gateway miguelperezcolom/mateu-article2-api-gateway:latest
docker image tag miguelperezcolom/mateu-article2-api-gateway miguelperezcolom/mateu-article2-api-gateway:$VERSION
echo "pushing latest"
docker push miguelperezcolom/mateu-article2-api-gateway:latest
echo "pushing $VERSION"
docker push miguelperezcolom/mateu-article2-api-gateway:$VERSION