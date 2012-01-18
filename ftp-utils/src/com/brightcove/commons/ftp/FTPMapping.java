package com.brightcove.commons.ftp;

/**
 * <p>
 *    Provides a mapping between a file on disk and a path on an FTP server
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public interface FTPMapping<S,D> {
	/**
	 * <p>
	 *    Sets the source for the FTP transaction
	 * </p>
	 * 
	 * @param source Source for the transaction - usually a File (local) or String (remote)
	 */
	public void setSource(S source);
	
	/**
	 * <p>
	 *    Returns the source for the FTP transaction
	 * </p>
	 * 
	 * @return Source for the transaction - usually a File (local) or String (remote)
	 */
	public S getSource();
	
	/**
	 * <p>
	 *    Sets the destination for the FTP transaction
	 * </p>
	 * 
	 * @param destination Destination for the transaction - usually a File (local) or String (remote)
	 */
	public void setDestination(D destination);
	
	/**
	 * <p>
	 *    Returns the Destination for the FTP transaction
	 * </p>
	 * 
	 * @return Destination for the transaction - usually a File (local) or String (remote)
	 */
	public D getDestination();
}
