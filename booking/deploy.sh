docker login
docker buildx build --platform=linux/x86 -t miguelperezcolom/mateu-article2-booking .
export VERSION=0.0.14
docker image tag miguelperezcolom/mateu-article2-booking miguelperezcolom/mateu-article2-booking:latest
docker image tag miguelperezcolom/mateu-article2-booking miguelperezcolom/mateu-article2-booking:$VERSION
echo "pushing latest"
docker push miguelperezcolom/mateu-article2-booking:latest
echo "pushing $VERSION"
docker push miguelperezcolom/mateu-article2-booking:$VERSION