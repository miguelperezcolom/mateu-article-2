docker login
docker buildx build --platform=linux/x86 -t miguelperezcolom/mateu-article2-financial .
export VERSION=0.0.11
docker image tag miguelperezcolom/mateu-article2-financial miguelperezcolom/mateu-article2-financial:latest
docker image tag miguelperezcolom/mateu-article2-financial miguelperezcolom/mateu-article2-financial:$VERSION
echo "pushing latest"
docker push miguelperezcolom/mateu-article2-financial:latest
echo "pushing $VERSION"
docker push miguelperezcolom/mateu-article2-financial:$VERSION