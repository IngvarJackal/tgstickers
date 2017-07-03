docker tag tgstickers_inservice ingvarjackal/tgstickers-inservice &&
docker tag tgstickers_blservice ingvarjackal/tgstickers-blservice &&
docker tag tgstickers_outservice ingvarjackal/tgstickers-outservice &&
docker push ingvarjackal/tgstickers-blservice &&
docker push ingvarjackal/tgstickers-inservice &&
docker push ingvarjackal/tgstickers-outservice
