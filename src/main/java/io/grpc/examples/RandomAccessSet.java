package io.grpc.examples;

import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A set which supports random access.
 */
final class RandomAccessSet<T> {
  private final Map<Node<T>, Node<T>> nodeMap = new HashMap<>();
  private final List<Node<T>> nodeList = new ArrayList<>();

  boolean isEmpty() {
    return nodeMap.isEmpty();
  }

  T getRandomKey() {
    Random r = new Random();
    return nodeList.get(r.nextInt(nodeList.size())).value;
  }

  boolean contains(T value) {
    return nodeMap.containsKey(new Node<>(value));
  }

  boolean add(T value) {
    Node<T> node = new Node<>(value);
    if (nodeMap.putIfAbsent(node, node) != null) {
      return false;
    }
    node.index = nodeList.size();
    nodeList.add(node);
    return true;
  }

  public boolean remove(T value) {
    Node<T> oldNode = nodeMap.remove(new Node<>(value));
    if (oldNode == null) {
      return false;
    }
    nodeList.set(oldNode.index, nodeList.get(nodeList.size() - 1));
    nodeList.get(oldNode.index).index = oldNode.index;
    nodeList.remove(nodeList.size() - 1);
    return true;
  }

  private static final class Node<T> {
    final T value;
    int index;

    Node(T value) {
      this.value = value;
      this.index = -1;
    }

    @Override
    public String toString() {
      return value.toString();
    }

    @Override
    public int hashCode() {
      return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Node) {
        return value.equals(((Node) obj).value);
      }
      return false;
    }
  }
}
