/*
package com.scor.rr.importBatch.utils;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class FileHadoopImpl implements FileHadoop {
	private static final Logger log = LoggerFactory.getLogger(FileHadoopImpl.class);
	private Configuration conf = new Configuration();
	private String pathKrb5ConfFile;
	private String pathAuthLoginConfFile;
	private String defaultFS;
	private String nameservices;
	private String nn1;
	private String nn2;
	private String principal;
	private String keyTabPath;
	private FileSystem hadoopFileSystem = null;

	public FileHadoopImpl(String pathKrb5ConfFile, String pathAuthLoginConfFile, String defaultFS, String nameservices,
						  String nn1, String nn2, String principal, String keyTabPath) throws URISyntaxException, IOException {

		this.pathKrb5ConfFile = pathKrb5ConfFile;
		this.pathAuthLoginConfFile = pathAuthLoginConfFile;
		this.defaultFS = defaultFS;
		this.nameservices = nameservices;
		this.nn1 = nn1;
		this.nn2 = nn2;
		this.principal = principal;
		this.keyTabPath = keyTabPath;

		log.info("path to KRB5 Conf : {}", this.pathKrb5ConfFile);
		log.info("path to JAAS Conf : {}", this.pathAuthLoginConfFile);
		log.info("path to KeyTab : {}", this.keyTabPath);


		//
		System.setProperty("java.security.krb5.conf", pathKrb5ConfFile);
		System.setProperty("java.security.auth.login.config", pathAuthLoginConfFile);
		System.setProperty("java.security.krb5.debug", "true");

		conf.set("hadoop.security.authentication", "kerberos");
		conf.set("fs.defaultFS", defaultFS);
		conf.set("dfs.nameservices", nameservices);
		conf.set("dfs.ha.namenodes." + nameservices, "nn1,nn2");
		conf.set("dfs.namenode.rpc-address." + nameservices + ".nn1", nn1);
		conf.set("dfs.namenode.rpc-address." + nameservices + ".nn2", nn2);
		conf.set("dfs.client.failover.proxy.provider." + nameservices,
				"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		conf.set("fs.hdfs.impl",
				org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		conf.set("fs.file.impl",
				org.apache.hadoop.fs.LocalFileSystem.class.getName());

		System.out.println("Conf set!");

		login();

		hadoopFileSystem = FileSystem.get(conf);

	}

	*/
/**
	 * Kerberos login with keytab
	 * 
	 * @return true if success
	 *//*

	public boolean login() {

		try {
			UserGroupInformation.setConfiguration(conf);
			UserGroupInformation.loginUserFromKeytab(principal, keyTabPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		log.info("user connected!!");

		return true;
	}

	*/
/**
	 * List files (and directories) in a path
	 * 
	 * @param path
	 * @return list of files (and directories) in path
	 * @throws IOException
	 *//*

	public List<String> listFiles(String path) throws IOException {
		return listFiles(path, false);
	}

	public List<String> listFiles(String path, boolean listDirectory)
			throws IOException {
		List<String> files = new ArrayList<String>();
		FileSystem fs = FileSystem.get(conf);
		FileStatus[] fsStatus = fs.listStatus(new Path(path));
		for (int i = 0; i < fsStatus.length; i++) {
			if (fsStatus[i].isFile() || listDirectory)
				files.add(fsStatus[i].getPath().toString());
			System.out.println(fsStatus[i].getPath().toString());
		}
		return files;
	}

	*/
/**
	 * create all folder in the path
	 * 
	 * @param path
	 *            example scor/dossier1/dossier2 , it relative path to the
	 *            actual working directory
	 * @return
	 *//*

	public Boolean createDirectories(String path) {
		try {
			String dirName = "TestDirectory2/test1";
			log.debug("creating directories :" + path
					+ " in actual WorkingDirectory : "
					+ hadoopFileSystem.getWorkingDirectory());
			Path hadoopPath = new Path(path);
			boolean res = hadoopFileSystem.mkdirs(hadoopPath);
			log.debug("directory" + hadoopPath + " created : " + res);
			return res;
		} catch (IOException e) {
			log.debug(e.getMessage());
		}
		return false;
	}

	*/
/**
	 * get content of a file in a array of bytes
	 * 
	 * @param path
	 * @return file content
	 * @throws IOException
	 *//*

	public byte[] getFile(String path) throws IOException {
		Path pt = new Path(path);
		FileSystem fs = FileSystem.get(conf);
		InputStreamReader is = new InputStreamReader(fs.open(pt));

		return IOUtils.toByteArray(is);
	}

	*/
/**
	 * Delete file in hadoop file system
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 *//*

	public boolean deleteFile(String path) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		Path filenamePath = new Path(path);

		if (fs.exists(filenamePath)) {
			fs.delete(filenamePath, true);
			return true;
		}

		return false;
	}

	*/
/**
	 * 
	 * @param StringPath
	 * @param hadoopPath
	 * @param bytes
	 * @return
	 * @throws IOException
	 *//*

	public boolean createFile(String stringPath, Path hadoopPath, byte[] bytes)
			throws IOException {
		if (stringPath != null) {
			Path src2 = new Path(stringPath);
		} else {
			//crer file.bin  � partir de path :  /test/test1/file.bin
			FSDataOutputStream outputStream = hadoopFileSystem
					.create(hadoopPath);
			outputStream.write(bytes);
		}

		return false;
	}

	public String getPathKrb5ConfFile() {
		return pathKrb5ConfFile;
	}

	public void setPathKrb5ConfFile(String pathKrb5ConfFile) {
		this.pathKrb5ConfFile = pathKrb5ConfFile;
	}

	public String getPathAuthLoginConfFile() {
		return pathAuthLoginConfFile;
	}

	public void setPathAuthLoginConfFile(String pathAuthLoginConfFile) {
		this.pathAuthLoginConfFile = pathAuthLoginConfFile;
	}

	public String getDefaultFS() {
		return defaultFS;
	}

	public void setDefaultFS(String defaultFS) {
		this.defaultFS = defaultFS;
	}

	public String getNameservices() {
		return nameservices;
	}

	public void setNameservices(String nameservices) {
		this.nameservices = nameservices;
	}

	public String getNn1() {
		return nn1;
	}

	public void setNn1(String nn1) {
		this.nn1 = nn1;
	}

	public String getNn2() {
		return nn2;
	}

	public void setNn2(String nn2) {
		this.nn2 = nn2;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getKeyTabPath() {
		return keyTabPath;
	}

	public void setKeyTabPath(String keyTabPath) {
		this.keyTabPath = keyTabPath;
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public FileSystem getFileSystem() {
		return hadoopFileSystem;
	}

	public void setFs(FileSystem fs) {
		this.hadoopFileSystem = fs;
	}

}

*/
