from concurrent import futures

import grpc
import helloworld_pb2
import helloworld_pb2_grpc

class Greeter(helloworld_pb2_grpc.GreeterServicer):
    def SayHello(self, request, context):
        return helloworld_pb2.HelloReply(message=f"Hello {request.name}, how are ya?")

def serve():
    port = "50051"
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))

    helloworld_pb2_grpc.add_GreeterServicer_to_server(Greeter(), server)

    server.add_insecure_port("[::]:"+port)
    server.start()

    print(f"Server listening on {port}")

    # Graceful shutdown
    try:
        server.wait_for_termination()
    except KeyboardInterrupt:
        print("Manual intervention, shutting down server ...")
        server.stop(2)
        print("Server stopped.")
    except Exception as err:
        print(f"Server encountered an error:\n[ ERROR ]{err}\nShutting down ...")
        server.stop(2)
        print("Server stopped.")

if __name__ == "__main__":
    serve()