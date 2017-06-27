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
stubBuildingProcess = subprocess.Popen("sh etc/test/stub/rebuild.sh", shell=True)
stubBuildingProcess.wait()
if (stubBuildingProcess.returncode != 0):
    print("ERROR DURING STUBS BUILDING, FURTHER EXECUTION ABORTED!")
    sys.exit(loginProcess.returncode)


print("\n++++++++++++++++++++++++++++++++++++++++++++ UNIT TESTING ++++++++++++++++++++++++++++++++++++++++++++")
mavenProcess = subprocess.Popen("sh etc/dev/rebuild.sh etc/test/docker-compose-test.yml", shell=True)
mavenProcess.wait()
if mavenProcess.returncode != 0:
    print("ERROR DURING UNIT TESTING, FURTHER EXECUTION ABORTED!")
    sys.exit(mavenProcess.returncode)


print("\n+++++++++++++++++++++++++++++++++++++++++ INTEGRATION TESTING ++++++++++++++++++++++++++++++++++++++++")
os.makedirs(os.path.dirname("/tmp/stubres/"), exist_ok=True)
if os.path.isfile("/tmp/stubres/result.txt"):
    os.chmod("/tmp/stubres/result.txt", stat.S_IWRITE)
    os.remove("/tmp/stubres/result.txt")
dockerComposeProcess = subprocess.Popen("./docker-compose -f etc/test/docker-compose-test.yml --project-directory . up --build", shell=True, preexec_fn=os.setsid)

SLEEPING_TIME = 60 # seconds
while not os.path.isfile("/tmp/stubres/result.txt"):
    time.sleep(1)
    SLEEPING_TIME -= 1
    if SLEEPING_TIME == 0:
        print("ERROR: SIMULATION TIME EXCEEDED")
        os.killpg(os.getpgid(dockerComposeProcess.pid), signal.SIGKILL)
        dockerComposeProcess.wait()
        sys.exit(123)

with open("/tmp/stubres/result.txt") as res:
    lines = res.readlines();
    for line in lines:
        if line.rstrip() != "OK":
            print("".join(lines))
            print("ERROR DURING INTEGRATION TESTING, FURTHER EXECUTION ABORTED!")
            os.killpg(os.getpgid(dockerComposeProcess.pid), signal.SIGKILL)
            dockerComposeProcess.wait()
            sys.exit(123)

os.killpg(os.getpgid(dockerComposeProcess.pid), signal.SIGKILL)
dockerComposeProcess.wait()


# print("\n++++++++++++++++++++++++++++++++++++++++++ IMAGE DEPLOYMENT ++++++++++++++++++++++++++++++++++++++++++")
# subprocess.Popen("sh etc/dev/push.sh", shell=True).wait()

print("\n+++++++++++++++++++++++++++++++++++++++++++++ TESTS DONE +++++++++++++++++++++++++++++++++++++++++++++")

sys.exit(0)