#source aws keys
source /apps/scripts/setenv_aws.sh 

#copy files from git to s3 bucket
for region in ue1 uw2 ew1 as1; do
aws s3 sync . s3://adobe-apiplatform-mc-stage-config-$region/$SIDE --exclude '*conf.d/campaign.conf*' --exclude '*generated-conf.d*' --exclude ".git*" --exclude '*Makefile*' --exclude '*README.md*' --exclude '*api-gateway-cache-upstream.conf*' --exclude '*logging.conf*' --delete --profile apimcSTAGE
done
