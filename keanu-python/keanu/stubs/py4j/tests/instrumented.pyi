# Stubs for py4j.tests.instrumented (Python 3.6)
#
# NOTE: This dynamically typed stub was automatically generated by stubgen.

from py4j.clientserver import ClientServer, ClientServerConnection, JavaClient, PythonServer
from py4j.java_gateway import CallbackConnection, CallbackServer, GatewayClient, GatewayConnection, GatewayProperty, JavaGateway
from py4j.tests.py4j_callback_recursive_example import PythonPing
from typing import Any

MEMORY_HOOKS: Any
CREATED: Any
FINALIZED: Any

def register_creation(obj: Any): ...

class InstrumentedPythonPing(PythonPing):
    def __init__(self, fail: bool = ...) -> None: ...

class InstrJavaGateway(JavaGateway):
    def __init__(self, *args: Any, **kwargs: Any) -> None: ...

class InstrGatewayClient(GatewayClient):
    def __init__(self, *args: Any, **kwargs: Any) -> None: ...

class InstrGatewayProperty(GatewayProperty):
    def __init__(self, *args: Any, **kwargs: Any) -> None: ...

class InstrGatewayConnection(GatewayConnection):
    def __init__(self, *args: Any, **kwargs: Any) -> None: ...

class InstrCallbackServer(CallbackServer):
    def __init__(self, *args: Any, **kwargs: Any) -> None: ...

class InstrCallbackConnection(CallbackConnection):
    def __init__(self, *args: Any, **kwargs: Any) -> None: ...

class InstrClientServerConnection(ClientServerConnection):
    def __init__(self, *args: Any, **kwargs: Any) -> None: ...

class InstrPythonServer(PythonServer):
    def __init__(self, *args: Any, **kwargs: Any) -> None: ...

class InstrJavaClient(JavaClient):
    def __init__(self, *args: Any, **kwargs: Any) -> None: ...

class InstrClientServer(ClientServer):
    def __init__(self, *args: Any, **kwargs: Any) -> None: ...
