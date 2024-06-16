import argparse
import locale
import os
import socket
import shlex
import subprocess
import sys
import textwrap
import threading


def execute(cmd):
    cmd = cmd.strip()
    if not cmd:
        return

    if os.name == 'nt':
        shell = True
    else:
        shell = False

    output = subprocess.check_output(shlex.split(cmd),
                                     stderr=subprocess.STDOUT,
                                     shell=shell)

    if locale.getdefaultlocale() == ('jp_JP', 'cp932'):
        return output.decode('cp932')
    else:
        return output.decode()


if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='BHP Net Tool',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog=textwrap.dedent(
            '''実行例:
            
        )
    )
