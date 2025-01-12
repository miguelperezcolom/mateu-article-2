docker login
docker buildx build --platform=linux/x86 -t miguelperezcolom/mateu-article2-shell .
export VERSION=0.0.4
docker image tag miguelperezcolom/mateu-article2-shell miguelperezcolom/mateu-article2-shell:latest
docker image tag miguelperezcolom/mateu-article2-shell miguelperezcolom/mateu-article2-shell:$VERSION
echo "pushing latest"
docker push miguelperezcolom/mateu-article2-shell:latest
echo "pushing $VERSION"
docker push miguelperezcolom/mateu-article2-shell:$VERSION