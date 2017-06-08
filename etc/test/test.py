#!/usr/bin/python3

import sys
import os
import stat
import subprocess
import signal
import time

print("\n+++++++++++++++++++++++++++++++++++++++++++++ SETTING UP +++++++++++++++++++++++++++++++++++++++++++++")
if os.environ.get("DOCKER_USERNAME") is None:
    print("ERROR DURING DOCKER LOGIN: DOCKER_USERNAME ISN'T SET, FURTHER EXECUTION ABORTED!")
    sys.exit(123)
if os.environ.get("DOCKER_PASSWORD") is None:
    print("ERROR DURING DOCKER LOGIN: DOCKER_PASSWORD ISN'T SET, FURTHER EXECUTION ABORTED!")
    sys.exit(123)
loginProcess = subprocess.Popen("docker login -u=\"$DOCKER_USERNAME\" -p=\"$DOCKER_PASSWORD\"", shell=True)
loginProcess.wait()
if (loginProcess.returncode != 0):
    print("ERROR DURING DOCKER LOGIN, FURTHER EXECUTION ABORTED!")
    sys.exit(loginProcess.returncode)


print("\n++++++++++++++++++++++++++++++++++++++++++++ UNIT TESTING ++++++++++++++++++++++++++++++++++++++++++++")
mavenProcess = subprocess.Popen("sh etc/dev/rebuild.sh etc/test/docker-compose-test.yml", shell=True)
mavenProcess.wait()
if mavenProcess.returncode != 0:
    print("ERROR DURING UNIT TESTING, FURTHER EXECUTION ABORTED!")
    sys.exit(mavenProcess.returncode)


# print("\n+++++++++++++++++++++++++++++++++++++++++ INTEGRATION TESTING ++++++++++++++++++++++++++++++++++++++++")
# dockerComposeProcess = subprocess.Popen("./docker-compose -f etc/test/docker-compose-test.yml --project-directory . up", shell=True, preexec_fn=os.setsid)
#
# if os.path.isfile("/tmp/stubres/result.txt"):
#     os.chmod("/tmp/stubres/result.txt", stat.S_IWRITE)
#     os.remove("/tmp/stubres/result.txt")
# while not os.path.isfile("/tmp/stubres/result.txt"): time.sleep(1)
# with open("/tmp/stubres/result.txt") as res:
#     for line in res.readlines():
#         if line != "OK":
#             print(line)
#             print("ERROR DURING INTEGRATION TESTING, FURTHER EXECUTION ABORTED!")
#             sys.exit(123)
#
# os.killpg(os.getpgid(dockerComposeProcess.pid), signal.SIGKILL)
# dockerComposeProcess.wait()
#
#
# print("\n++++++++++++++++++++++++++++++++++++++++++ IMAGE DEPLOYMENT ++++++++++++++++++++++++++++++++++++++++++")
# subprocess.Popen("sh etc/dev/push.sh", shell=True).wait()


sys.exit(0)