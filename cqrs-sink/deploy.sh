docker login
docker buildx build --platform=linux/x86 -t miguelperezcolom/mateu-article2-cqrs-sink .
export VERSION=0.0.1
docker image tag miguelperezcolom/mateu-article2-cqrs-sink miguelperezcolom/mateu-article2-cqrs-sink:latest
docker image tag miguelperezcolom/mateu-article2-cqrs-sink miguelperezcolom/mateu-article2-cqrs-sink:$VERSION
echo "pushing latest"
docker push miguelperezcolom/mateu-article2-cqrs-sink:latest
echo "pushing $VERSION"
docker push miguelperezcolom/mateu-article2-cqrs-sink:$VERSION