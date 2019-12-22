package compiler.cache;

import compiler.errors.CompileError;
import java.util.ArrayDeque;

public class Cache {
  private ArrayDeque<CacheEntry> entries;

  public Cache() {
    entries = new ArrayDeque<>();
  }

  public void push(CacheEntry entry) {
    entries.addFirst(entry);
  }

  public CacheEntry pop() throws CompileError {
    if (entries.size() == 0)
      throw new CompileError("Cache is empty", "get from cache");


    return entries.pollFirst();
  }
}
