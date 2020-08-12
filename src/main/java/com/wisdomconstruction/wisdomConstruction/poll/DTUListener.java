
package com.wisdomconstruction.wisdomConstruction.poll;

import com.wisdomconstruction.wisdomConstruction.tool.StartThread;

import java.io.IOException;

/**
 *
 * 文件描述:(DTU端口监听测试)
 *
 */
public class DTUListener {

	/**   
	 * DTU 调用方法
	 * main  
	 * (这里描述这个方法适用条件 – 可选)  
	 * @param args：   void
	 */
	public static void main(String[] args) throws IOException {
		StartThread startThread = new StartThread();
		startThread.start();
	}

}
